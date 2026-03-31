provider "aws" {
  region = var.region
}

resource "aws_instance" "db" {
  ami           = "ami-0dab98137e5c11cb8" # Ubuntu Server 24.04 LTS AMI
  instance_type = var.instance_type_db
  key_name      = var.key_name
  vpc_security_group_ids      = [aws_security_group.temp_ssh_sg.id]
  associate_public_ip_address = true

  tags = {
    Name = "civilregistry-db"
  }



user_data = <<-EOF
              #!/bin/bash
              #install MySQL server
              sudo apt-get update
              sudo apt-get install -y mysql-server

              # Configure MySQL to allow remote connections
              sudo sed -i 's/^bind-address\s*=.*$/bind-address = 0.0.0.0/' /etc/mysql/mysql.conf.d/mysqld.cnf

              # Restart MySQL service
              sudo systemctl restart mysql

              # create non-root user and CivilRegistry db that this user has full access to
              sudo mysql -u root -e "CREATE USER '${var.db_user}'@'%' IDENTIFIED BY '${var.db_password}';"
              sudo mysql -u root -e "CREATE DATABASE ${var.db_name};"
              sudo mysql -u root -e "GRANT ALL PRIVILEGES ON ${var.db_name}.* TO '${var.db_user}'@'%' WITH GRANT OPTION;"
              sudo mysql -u root -e "FLUSH PRIVILEGES;"
              touch /tmp/user_data_complete
              EOF
}


# Poll for instance status to ensure user data script completes
resource "null_resource" "wait_for_db_instance" {
  depends_on = [aws_instance.db]

  provisioner "remote-exec" {
    inline = [
      "while [ ! -f /tmp/user_data_complete ]; do sleep 10; done",
      "echo 'User-data script completed'"
    ]

    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file("${path.module}/cloud_test.pem")
      host        = aws_instance.db.public_ip
    }
  }
}

resource "aws_ami_from_instance" "db_ami" {
  name               = "db-ami"
  source_instance_id = aws_instance.db.id

  tags = {
    Name = "civilregistry-db-ami"
  }

  depends_on = [null_resource.wait_for_db_instance]
}


resource "aws_instance" "app" {
  ami           = "ami-0dab98137e5c11cb8"  # Ubuntu Server 24.04 LTS AMI
  instance_type = var.instance_type_app
  key_name      = var.key_name
  associate_public_ip_address = true
  vpc_security_group_ids      = [aws_security_group.temp_ssh_sg.id]
  tags = {
    Name = "spring-boot-setup"
  }

  user_data = <<-EOF
              #!/bin/bash
              sudo apt-get update -y
              sudo apt-get install -y openjdk-21-jdk git maven
              git clone ${var.spring_boot_app_git_repo} /home/ubuntu/app
              cd /home/ubuntu/app
              git fetch
              git checkout -b ${var.git_repo_branch} origin/${var.git_repo_branch}
              cd civilregistry-data
              mvn clean package
              touch /tmp/user_data_complete
              EOF
}


# Poll for instance status to ensure user data script completes
resource "null_resource" "wait_for_app_instance" {
  depends_on = [aws_instance.app]

  provisioner "remote-exec" {
    inline = [
      "while [ ! -f /tmp/user_data_complete ]; do sleep 10; done",
      "echo 'User-data script completed'"
    ]

    connection {
      type        = "ssh"
      user        = "ubuntu"
      private_key = file("${path.module}/cloud_test.pem")
      host        = aws_instance.app.public_ip
    }
  }
}

resource "aws_ami_from_instance" "app_ami" {
  name               = "app-ami"
  source_instance_id = aws_instance.app.id

  tags = {
    Name = "civilregistry-app-ami"
  }





  depends_on = [null_resource.wait_for_app_instance]



}


resource "aws_security_group" "temp_ssh_sg" {
  name        = "temp_ssh_sg"
  description = "Temp SG for AMI creation"

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
