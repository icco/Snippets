# Snippets

Android App for journaling.

[![Build Status](https://travis-ci.org/icco/Snippets.svg?branch=master)](https://travis-ci.org/icco/Snippets)

## History & Ideology

I swear I've started writing this app like four million fucking times. See https://github.com/icco/Highlight as the most recent one I think. The general goal of this app is to let people write notes about their life, to remind them to write more notes, and then to provide sentiment analyses to the user, reminding them of the good things in their lives.

## Install

To join my faithful group of Alpha Testers, do the following:

 1. Join this Google group: https://groups.google.com/forum/#!forum/devcloud
 2. Visit https://play.google.com/apps/testing/org.devcloud.snippets.app
 3. Install the app!

Note: I value security highly, so I'll try and keep everything secure. One thing I can't promise on Alpha builds is data integrity. I am running daily backups of the database, but I can totally see everything breaking and you losing a few posts.

## Release

 * `./gradlew assembleRelease && cp app/build/outputs/apk/app-release.apk ~/Desktop/; ./gradlew clean`
