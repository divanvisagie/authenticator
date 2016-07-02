# Swiss Guard

[![Build Status](https://travis-ci.org/divanvisagie/swiss-guard.svg?branch=master)](https://travis-ci.org/divanvisagie/swiss-guard) [![Docker Pulls](https://img.shields.io/docker/pulls/divanvisagie/swissguard.svg?maxAge=2592000)](https://hub.docker.com/r/divanvisagie/swissguard)

A Finatra Thrift server application for users and authentication.

![Swiss Guard Flag](./documentation/regimental_flag.jpg)

## Running

### Migrations

```sh
sbt server/flywayMigrate
```

### Update Submodules

```sh
git submodule update --init --recursive
```

### Environment variables

#### Postgres

Name           | Description                | Default
-------------- | -------------------------- | ---------------
SG_PG_INET     | Postgres url               | localhost:54321
SG_PG_USERNAME | Postgres database username | postgres
SG_PG_PASSWORD | Postgres database password | postgres

#### Tokens

Name            | Description                | Default
--------------- | -------------------------- | -------
SG_TOKEN_SECRET | Secret used to sign jwt tokens | magnets

## License

Copyright 2016 Divan Visagie

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
