# CrowdKit

## Overview

CrowdKit is a programming framework designed to provide developers with a comprehensive tool to simplify the process of applications development in Mobile CrowdSensing(MCS). 

## Architecture

### Web-Side

The web interface provides developers with a visual programming environment for defining data models and selecting algorithms through clicks, selections, and configurations.

### Server-Side

The server-side consists of entity models, code generation modules, algorithm selection modules, and communication modules. It handles communication between the web interface and the client-side, providing abstract tasks and entity models. It generates personalized models based on developer instructions and automatically generates business code. The server-side is built on the Spring Boot framework, utilizing technologies such as mybatis, rabbitmq, freemarker, and integrates domain-specific algorithms into a library for developer selection.

### Client-Side

The client-side consists of perception and communication modules, responsible for executing perception tasks and completing data operations. It communicates with the server-side through communication modules for tasks such as perception task publishing, perception task assignment, and perception data uploading. The client-side backend is based on the Android platform.

## Usage

### Server-Side Configuration

1. **Dependency Installation:** Ensure the installation of dependencies such as Spring Boot, mybatis, rabbitmq, etc.
2. **Configuration File:** Configure relevant parameters in the server-side project, such as database connection and message queue.

### Web Interface Usage

1. **Dependency Installation:** Install Node.js and npm.
2. **Project Initialization:** Navigate to the web interface project directory, run `npm install` to install project dependencies.
3. **Launch:** Run `npm run serve` to start the web interface.

### Client-Side Configuration

1. **Install Android Studio:** Ensure the installation of Android Studio.
2. **Import Project:** Open the client-side project, configure the Android development environment.


## Project Status

Currently, CrowdKit is in the submission and review stage. 
