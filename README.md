# Jenkinstein

    “Beware; for I am fearless, and therefore powerful.” 
    ― Mary Shelley, Frankenstein

Jenkinstein brings your Jenkins server to life and gives it a voice.
It can synthesize text to speech and play audio files stored on servers file system.

Powered by <a href="http://mary.dfki.de/">MaryTTS</a>

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Usage

To start a web server for the application, run:

    lein run
    
List stored local sounds.
    
    curl -XGET localhost:3000/list 
    
Play store local sound given its filename.
    
    curl -XGET localhost:3000/play/<filename>
    
Speak then given text.

    curl -XGET localhost:3000/speak/<text>
    
Let Jenkinstein react to completed builds with sounds or synthesized speech with the 
[Post Completed Build Result Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Post+Completed+Build+Result+Plugin).

When configured to send notifications to Jenkinstein your configured sounds are automatically played. Internally 
Jenkinstein uses the [Remote access API](https://wiki.jenkins-ci.org/display/JENKINS/Remote+access+API) to obtain the 
job details then.
 
 As "Ping Url" configure 
     
     "http://<jenkins-host>:<port>/notify"

The default port is 3000 but it can be configured in dev and prod config.edn or on the commandline with 

    lein run -p <port>

## License

Copyright © 2016

MIT license
