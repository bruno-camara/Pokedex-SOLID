#!/bin/bash


# Check for python exec, either `python` or `python3`

if [ -x "$(command -v python)" ]; then
  PYTHON_EXEC=python
elif [ -x "$(command -v python3)" ]; then
  PYTHON_EXEC=python3
fi


if [ -z "$PYTHON_EXEC" ]; then
  echo "No installation of python found"
  exit 1
fi


PYTHON_VERSION=$($PYTHON_EXEC -V 2>&1 | sed 's/.* \([0-9]\).[0-9].*/\1/')
if [[ "$PYTHON_VERSION" -ge "3" ]]
then 
    # Python 3
    $PYTHON_EXEC -m http.server 8000
else
    # Python 2
    $PYTHON_EXEC -m SimpleHTTPServer 8000
fi


