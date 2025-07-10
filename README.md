# Appointment Coordination Application

This project is a simple web application for coordinating appointments. Users can propose an event with several time slots, and other participants can reply with their availability.

## Overview

The application consists of three main pages:

1.  **Create Page (`/create`):** Allows a user to propose a new appointment coordination. The user can provide a title, a description, their name, and between one and three time suggestions.
2.  **Reply Page (`/reply/{id}`):** Allows a participant to view the details of a specific appointment and submit their availability for the suggested time slots.
3.  **Overview Page (`/overview`):** Displays a list of all created appointment coordinations, shows their status (e.g., "Replied" or a button to reply), and provides navigation to the relevant pages.

## How to Run

There are three main ways to run this application: with Docker Compose, or in a Kubernetes cluster (Minikube) using either HAProxy or Ingress.

### 1. Running with Docker Compose & HAProxy

This method starts the complete architecture with two app instances, a database, and a load balancer on your local machine. This uses the separate services approach.

1.  **Prerequisites:**
    * Docker and Docker Compose installed.
    * Ensure port 80 and 27017 are free on your machine. Stop any other running MongoDB containers (like `dev-mongo`).
2.  **Build and Start:**
    Navigate to the project's root directory (where `docker-compose.yml` is located) and run:
    ```bash
    docker-compose up --build -d
    ```
    The `--build` flag ensures that your custom `appointment-app` and `mongodb_custom` images are built from their respective Dockerfiles.
3.  **Access the Application:**
    * **Main App:** `http://localhost/overview`
    * **HAProxy Stats:** `http://localhost/haproxy?stats`
    * **Database (with Compass):** Connect to `mongodb://127.0.0.1:27017`
4.  **Stop the Environment:**
    ```bash
    docker-compose down -v
    ```

### 2. Running with Kubernetes (Minikube) & HAProxy

This method deploys the application to Kubernetes using HAProxy and a `NodePort` service to expose the application.

1.  **Prerequisites:**
    * Minikube installed and running (`minikube start --memory 4g --cpus 2`).
2.  **Build and Load Images into Minikube:**
    ```bash
    # Build the images first
    docker build -t appointment-app-img ./appointment-scheduler
    docker build -t custom-mongo-img ./mongodb_custom
    
    # Load them into Minikube
    minikube image load appointment-app-img
    minikube image load custom-mongo-img
    ```
3.  **Apply Kubernetes Configurations:**
    This command applies the configurations for MongoDB, the app, and HAProxy. **It ignores the `04-ingress.yaml` file.**
    ```bash
    kubectl apply -f k8s/01-mongodb.yaml
    kubectl apply -f k8s/02-appointment-app.yaml
    kubectl apply -f k8s/03-haproxy.yaml
    ```
4.  **Access the Application:**
    Use the `minikube service` command to get the correct URL.
    ```bash
    minikube service haproxy-loadbalancer-service --url
    ```
    Open the URL provided by the command (e.g., `http://192.168.49.2:31234`) in your browser and add `/overview` to the end.
5.  **Stop and Clean Up:**
    To remove only the HAProxy resources, you can run:
    ```bash
    kubectl delete -f k8s/03-haproxy.yaml
    ```
    To remove all deployed resources (App, MongoDB, and HAProxy), run:
    ```bash
    kubectl delete -f k8s/
    ```
    Stop Minikube: `minikube stop`.

### 3. Running with Kubernetes (Minikube) & Ingress

This is the final and most advanced setup, deploying the application to a local Kubernetes cluster and using an Ingress to manage traffic with a custom hostname.

1.  **Prerequisites:**
    * Minikube installed and running (`minikube start --memory 4g --cpus 2`).
2.  **Enable Ingress Addon:**
    This only needs to be done once per Minikube cluster.
    ```bash
    minikube addons enable ingress
    ```
3.  **Build and Load Images into Minikube:**
    Your local Docker images need to be made available to Minikube's internal Docker environment.
    ```bash
    # Build the images first
    docker build -t appointment-app-img ./appointment-scheduler
    docker build -t custom-mongo-img ./mongodb_custom
    
    # Load them into Minikube
    minikube image load appointment-app-img
    minikube image load custom-mongo-img
    ```
4.  **Apply Kubernetes Configurations:**
    This command applies the configurations for MongoDB, the app, and the Ingress. **It ignores the `03-haproxy.yaml` file.** Ensure your `k8s/04-ingress.yaml` uses `host: appointment-app.naren`.
    ```bash
    kubectl apply -f k8s/01-mongodb.yaml
    kubectl apply -f k8s/02-appointment-app.yaml
    kubectl apply -f k8s/04-ingress.yaml
    ```
5.  **Set up `minikube tunnel` and Hostname:**
    To access the Ingress via a custom hostname (`appointment-app.naren`), you need two things:

    * **Start the Tunnel:** Open a **new, separate terminal window** and run:
        ```bash
        minikube tunnel
        ```
      Enter your Mac/Linux password when prompted and **leave this terminal window running**.
    * **Edit `/etc/hosts` file:**
        * First, get the IP address assigned by the tunnel. In your first terminal, run `kubectl get ingress` and wait for an IP to appear in the `ADDRESS` column (usually `127.0.0.1`).
        * Open your hosts file with administrator privileges: `sudo nano /etc/hosts`.
        * Add the following line at the end and save the file:
            ```
            127.0.0.1    appointment-app.naren
            ```
6.  **Access the Application:**
    You can now access your application in the browser using the custom hostname:
    * **Overview Page:** `http://appointment-app.naren/overview`
    * **Create Page:** `http://appointment-app.naren/create`
7.  **Stop and Clean Up:**
    * Stop the `minikube tunnel` process in its terminal window with `Ctrl+C`.
    * Remove the line you added from your `/etc/hosts` file.
    * Delete all Kubernetes resources:
        ```bash
        kubectl delete -f k8s/01-mongodb.yaml
        kubectl delete -f k8s/02-appointment-app.yaml
        kubectl delete -f k8s/04-ingress.yaml
        ```
    * Stop Minikube: `minikube stop`.
