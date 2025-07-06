#!/bin/bash
#set -x
# Verifies the presence of development tools needed for the project

# Color definitions for better readability
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "=== Development Environment Check ==="
echo "Suggestions written for OSX, Linux users should use their package manager of choice."

# Detect operating system
IS_WINDOWS=false
if [[ "$OSTYPE" == "msys"* ]] || [[ "$OSTYPE" == "cygwin"* ]] || [[ "$OSTYPE" == "win"* ]]; then
  IS_WINDOWS=true
  echo "Windows OS detected: truly not recommended for any serious work."
  echo "Please consider using WSL2 (Windows Subsystem for Linux) or a Linux VM if you must use OS from MacroHard company"
  echo "The following checks may not work properly on Windows."
fi

# Check if a command exists
command_exists() {
  command -v "$1" >/dev/null 2>&1
}

echo -n "Checking yq: "
if command_exists yq; then
  echo -e "${GREEN}Found $(yq --version)"
else
  echo -e "${RED}Not found${NC}"
  echo -e "${YELLOW}Installation suggestion:${NC}"
  echo "  Install with homebrew: brew install yq"
fi

echo "Checking Maven: "
if command_exists mvn; then
  echo -e "${GREEN}Found $(mvn --version | head -n 1 | awk '{print " (" $3 ")"}')"
else
  echo -e "${RED}Not found${NC}"
  echo -e "${YELLOW}Installation suggestion:${NC}"
  echo -e "  Install with SDKMAN: sdk install maven"
fi

echo "Checking Maven Daemon (mvnd): "
if command_exists mvnd; then
  echo -e "${GREEN}Found $(mvnd --version | head -n 1 )"
else
  echo -e "${RED}Not found ${NC}"
  echo -e "${YELLOW}Installation suggestion:${NC}"
  echo "  Install with SDKMAN: sdk install mvnd"
fi

# Check for PostgreSQL
echo -n "Checking PostgreSQL: "
if command_exists pg_isready && pg_isready >/dev/null 2>&1; then
  echo -e "${GREEN}Found and running $(postgres -V | awk '{print " (" $3 ")"}')"
elif command_exists postgres; then
  echo -e "${YELLOW}Found but not running$(postgres -V | awk '{print " (" $3 ")"}')"
  echo "  Start PostgreSQL with: brew services start postgresql"
else
  echo -e "${RED}Not found${NC}"
  echo -e "${YELLOW}Installation suggestion:${NC}"
  echo "  Install with Homebrew: brew install postgresql"
  echo "  Start service with: brew services start postgresql"
fi

  echo -n "Checking psql tool: "
  if command_exists psql; then
    echo -e "${GREEN}Found${NC}"
    echo "Attempting to connect to 'fss' database..."
  else
    echo -e "${YELLOW}Not found${NC}"
    echo "While psql is not strictly required for the project, it is useful for database management."
    echo "Please use your favorite DB management tool to create 'fss' database."
    echo "DBeaver is a good choice for a GUI tool for example."
  fi
