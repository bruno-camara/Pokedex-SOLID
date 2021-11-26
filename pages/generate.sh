#!/bin/bash

mkdir -p pages/build

# Make TP subject HTML page
cd sujet_TP && make && cd ..
cp -R sujet_TP/sujet.html sujet_TP/ressources pages/build


# Make presentation HTML page
pwd
cd presentation && make && cd ..
cp presentation/presentation.html pages/build


# Copy all static HTML files and assets
cp -r pages/static/* pages/build
