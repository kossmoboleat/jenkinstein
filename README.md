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

## License

Copyright © 2016

MIT license
