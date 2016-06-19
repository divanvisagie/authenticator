#!/usr/bin/env bash
sbt server/docker:stage
docker build -t divanvisagie/swissguard server/target/docker/stage
docker push divanvisagie/swissguard
