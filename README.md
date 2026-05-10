# Generateur-motsdepasse
version: '3.8'

services:
  password-generator:
    build: .
    stdin_open: true  # Pour mode interactif
    tty: true
    volumes:
      - ./src:/app
    command: ["java", "Main"]
    
  # Service de validation (exemple avec API de vérification)
  strength-validator:
    image: node:18-alpine
    command: sh -c "npm install -g zxcvbn && node -e 'const zxcvbn = require(\"zxcvbn\"); process.stdin.on(\"data\", d => console.log(zxcvbn(d.toString().trim()).score))'"
    # En production, utiliser une API REST dédiée