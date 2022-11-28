#!/bin/bash

mkdir -p pages/build/TP
mkdir -p pages/build/presentation

# Make TP subject HTML page
cd sujet_TP && make && cd ..
cp -R sujet_TP/sujet.html sujet_TP/ressources pages/build/TP


# Make presentation HTML page
cp -R presentation pages/build/presentation


# Copy all static HTML files and assets
cp -r pages/static/* pages/build
