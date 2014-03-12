# humanhash

Groovy implementation of human readable hashes, compatible with https://github.com/zacharyvoase/humanhash ie. it uses an identical word list and hash 'translation' function.

## Example usage

```groovy
import com.whartonlabs.humanhash.HumanHasher

String digest = "7528880a986c40e78c38115e640da2a1"

/*
  Pass in a digest that contains only hexidecimal characters (0-9 and a-f), 
  and get back a human readable translation that's 4 words long, each word from a dictionary of 256 words.
  Using 4 words, there are 2^32 possible hashes, or ~ 4.3 billion
 */
assert new HumanHasher().humanize(digest) == "three-georgia-xray-jig"

/* 
  You can change the fidelity of the humanized hash by requesting a different number of words, 
  giving the number as a second argument
 */
assert new HumanHasher().humanize(digest, 6) == "high-mango-white-oregon-purple-charlie"

/*
  You can also pass in your own custom word list to the constructor, 
  useful for improving recognition for usage with languages other than english.
 */
def customWordList = (0..255).collect { "alfa" } // not a useful wordlist, but demonstrates the point...
assert new HumanHasher(customWordList).humanize(digest) == "alfa-alfa-alfa-alfa"

```
That's really all there is to it!


## Running the tests

Comes with Gradle wrapper, so nothing to install. Just navigate to the repository's root directory (where the build.gradle file is) and run

    ./gradlew test

## Maven

Currently available from the OSS Sonatype repository at https://oss.sonatype.org/content/repositories/snapshots

Your Gradle dependency would be

    compile 'com.whartonlabs:humanhash:0.2-SNAPSHOT'


