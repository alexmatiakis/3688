variable "region" {
  description = "The AWS region to deploy to"
  type        = string
  default     = "eu-north-1"
}

variable "instance_type_app" {
  description = "EC2 instance type for the Spring Boot application"
  type        = string
  default     = "t3.micro"
}

variable "instance_type_db" {
  description = "EC2 instance type for the Book DB"
  type        = string
  default     = "t3.micro"
}

variable "db_user" {
  description = "Username for the MySQL database"
  type        = string
  default   = "appuser"
}

variable "db_password" {
  description = "Password for the MySQL database"
  type        = string
  sensitive   = true
}

variable "db_name" {
  description = "Name of the MySQL database"
  type        = string
  default     = "civils"
}

variable "vpc_id" {
  description = "VPC ID for the infrastructure"
  type        = string
  default     = "vpc-05661916ecbb81278"
}

variable "subnets" {
  description = "Subnets for the load balancer"
  type        = list(string)
  default     = ["subnet-0de7280ea7c746f79", "subnet-0fbfcefbe9dc22869"]
}

variable "key_name" {
  description = "The name of the SSH key pair"
  type        = string
  default     = "cloud_test"
}

variable "jar_name" {
  description = "The name of the app jar file"
  type        = string
  default     = "civilregistry-data-0.0.1-SNAPSHOT"
}