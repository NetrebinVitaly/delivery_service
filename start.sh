#!/bin/bash

# Pull new changes
git init
git config --global --add safe.directory '*'
git pull https://github.com/Burgoy/delivery_service.git main


# Start new deployment with provided env vars in ./
docker-compose -f docker-compose.yml up -d
