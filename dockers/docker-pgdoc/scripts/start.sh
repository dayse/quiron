#!/bin/bash

# Stop on error
set -e

DATA_DIR=/data


# Start postgresql_autodoc
echo "Generating png for $DB_PORT_5432_TCP_ADDR $DB_ENV_DB_NAME"
echo "User and pass: $DB_ENV_POSTGRES_USER $DB_ENV_POSTGRES_PASSWORD"

postgresql_autodoc -t dot -h $DB_PORT_5432_TCP_ADDR -d $DB_ENV_DB_NAME -u $DB_ENV_POSTGRES_USER --password=$DB_ENV_POSTGRES_PASSWORD
dot -Tpng $DATA_DIR/$DB_ENV_DB_NAME.dot -o $DATA_DIR/$DB_ENV_DB_NAME.png
