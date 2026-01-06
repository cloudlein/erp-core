# Deployment Guide

## Building the Artifact

To create an executable JAR file:

```bash
./gradlew clean build -x test
```

The JAR file will be generated in `build/libs/erp-core-0.0.1-SNAPSHOT.jar`.

## Deployment Options

### Option A: Docker (Recommended)

1.  **Build the Docker Image:**

    ```bash
    docker build -t erp-core:latest .
    ```

2.  **Run the Container:**

    ```bash
    docker run -d \
      -p 8080:8080 \
      -e SPRING_PROFILES_ACTIVE=prod \
      -e DB_HOST=your-db-host \
      -e DB_PASSWORD=your-db-password \
      --name erp-core \
      erp-core:latest
    ```

### Option B: Traditional Server (Systemd)

1.  Copy the JAR file to your server.
2.  Create a systemd service file `/etc/systemd/system/erp-core.service`:

    ```ini
    [Unit]
    Description=ERP Core Service
    After=network.target

    [Service]
    User=appuser
    ExecStart=/usr/bin/java -jar -Dspring.profiles.active=prod /opt/erp/erp-core.jar
    SuccessExitStatus=143

    [Install]
    WantedBy=multi-user.target
    ```

3.  Start the service:

    ```bash
    sudo systemctl enable erp-core
    sudo systemctl start erp-core
    ```
