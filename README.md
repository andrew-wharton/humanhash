# humanhash

Groovy implementation of human readable hashes, compatible with https://github.com/zacharyvoase/humanhash ie. it uses an identical word list and hash function.

## Example usage

```groovy
import com.whartonlabs.humanhash.HumanHasher

String digest = "7528880a986c40e78c38115e640da2a1"

// pass in a hash that contains only hexidecimal characters (0-9 and a-f), and get back 4 pseudorandom words from the wordlist
assert new HumanHasher.humanize(digest) == "three-georgia-xray-jig"

// or if you want a different number of words, give hte number as a second argument
assert new HumanHasher.humanize(digest, 6) == "high-mango-white-oregon-purple-charlie"
```
That's really all there is to it!

## Running the tests

Comes with Gradle wrapper, so nothing to install. Just navigate to the repository's root directory (where the build.gradle file is) and run

>>> ./gradle test




