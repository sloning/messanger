#!/bin/bash
# source: https://www.mrnakumar.com/docker_mongo_security/

set -e

echo ">>>>>>> trying to create database and users"
if [ -n "${MONGO_INITDB_ROOT_USERNAME:-}" ] && [ -n "${MONGO_INITDB_ROOT_PASSWORD:-}" ]; then
mongo -u $MONGO_INITDB_ROOT_USERNAME -p $MONGO_INITDB_ROOT_PASSWORD<<EOF
db=db.getSiblingDB('messenger');
db.createUser({
  user: '$MONGO_INITDB_ROOT_USERNAME',
  pwd: '$MONGO_INITDB_ROOT_PASSWORD',
  roles: [{
    role: 'readWrite',
    db: 'messenger'
  }]
});
EOF
else
    echo "MONGO_INITDB_ROOT_USERNAME and MONGO_INITDB_ROOT_PASSWORD must be provided. Some of these are missing, hence exiting database and user creation"
    exit 403
fi