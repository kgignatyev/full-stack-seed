#!/bin/bash
#set -x
# Verifies the presence of development tools needed for the project

# final status, nonzero if any issue is found
STATUS=0

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

if [[ -z "${JAVA_HOME}" ]]; then
  STATUS=1
  echo -e "${RED}JAVA_HOME is not set${NC}"
  echo -e "${YELLOW}Set it with: export JAVA_HOME=<JDK_location>${NC}"
else
  echo -e "${GREEN}JAVA_HOME is set to: ${JAVA_HOME}${NC}"
fi

if [[ -x "${JAVA_HOME}/bin/javac" ]]; then
  echo -e "${GREEN}javac found at ${JAVA_HOME}/bin/javac${NC}"
else
  STATUS=1
  echo -e "${RED}javac not found at ${JAVA_HOME}/bin/javac${NC}"
  echo -e "${YELLOW}Please ensure JAVA_HOME points to a valid JDK installation.${NC}"
fi


# Check if a command exists
command_exists() {
  command -v "$1" >/dev/null 2>&1
}

echo  "Checking yq: "
if command_exists yq; then
  echo -e "\t${GREEN}Found $(yq --version)"
else
  STATUS=1
  echo -e "\t${RED}Not found${NC}"
  echo -e "\t${YELLOW}Installation suggestion:${NC}"
  echo "  Install with homebrew: brew install yq"
fi

echo  "Checking docker: "
if command_exists docker; then
  echo -e "\t${GREEN}Found $(docker --version)"
else
  STATUS=1
  echo -e "\t${RED}Not found${NC}"
  echo -e "\t${YELLOW}Installation suggestion:${NC}"
  echo "  visit https://www.docker.com/ and follow instructions"
fi

echo "Checking Maven: "
if command_exists mvn; then
  echo -e "\t${GREEN}Found $(mvn --version | head -n 1 | awk '{print " (" $3 ")"}')"
else
  STATUS=1
  echo -e "\t${RED}Not found${NC}"
  echo -e "\t${YELLOW}Installation suggestion:${NC}"
  echo -e "  Install with SDKMAN: sdk install maven"
fi

echo "Checking Maven Daemon (mvnd): "
if command_exists mvnd; then
  echo -e "\t${GREEN}Found $(mvnd --version | head -n 1 )"
else
  STATUS=1
  echo -e "\t${RED}Not found ${NC}"
  echo -e "\t${YELLOW}Installation suggestion:${NC}"
  echo "  Install with SDKMAN: sdk install mvnd"
fi

echo "Checking Temporal workflow management : "
if command_exists temporal; then
  echo -e "\t${GREEN}Found $(temporal --version | head -n 1 )"
else
  STATUS=1
  echo -e "\t${RED}Not found ${NC}"
  echo -e "\t${YELLOW}Installation suggestion:${NC}"
  echo "  Install with homebrew: brew install temporal"
fi

# Check for PostgreSQL
echo -n "Checking PostgreSQL: "
if command_exists pg_isready && pg_isready >/dev/null 2>&1; then
  echo -e "${GREEN}Found and running $(postgres -V | awk '{print " (" $3 ")"}')"
elif command_exists postgres; then
  echo -e "${YELLOW}Found but not running$(postgres -V | awk '{print " (" $3 ")"}')"
  echo "  Start PostgreSQL with: brew services start postgresql"
else
  STATUS=1
  echo -e "${RED}Not found${NC}"
  echo -e "${YELLOW}Installation suggestion:${NC}"
  echo "  Install with Homebrew: brew install postgresql"
  echo "  Start service with: brew services start postgresql"
fi

  echo -n "Checking psql tool: "
  if command_exists psql; then
    echo -e "${GREEN}Found${NC}"
    echo "Attempting to connect to 'fss' database... not implemented yet"
  else
    echo -e "${YELLOW}Not found${NC}"
    echo "While psql is not strictly required for the project, it is useful for database management."
    echo "Please use your favorite DB management tool to create 'fss' database."
    echo "DBeaver is a good choice for a GUI tool for example."
  fi

echo "Recommended tools:"
echo "  DBeaver is a good DB GUI tool https://dbeaver.io/"
echo "  SDKMAN for managing java tools and SDK-s https://sdkman.io/"

if ( [ "${STATUS}" -eq 0 ] ); then
	echo -e "${GREEN}All required tools are installed and properly configured.${NC}"
else
	echo -e "${RED}Some required tools are missing or not properly configured.${NC}"
fi
exit "${STATUS}"