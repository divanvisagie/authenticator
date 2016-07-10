#!/usr/bin/env bash
sbt auth/docker:stage
docker build -t divanvisagie/swissguard-auth authentication/target/docker/stage

