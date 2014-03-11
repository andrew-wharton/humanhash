package com.whartonlabs.humanhash

/**
 * Created with IntelliJ IDEA.
 * User: andrewwharton
 * Date: 11/03/2014
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */

class HumanHasher {

    private static final List<String> DEFAULT_WORD_LIST = [
        'ack', 'alabama', 'alanine', 'alaska', 'alpha', 'angel', 'apart', 'april',
        'arizona', 'arkansas', 'artist', 'asparagus', 'aspen', 'august', 'autumn',
        'avocado', 'bacon', 'bakerloo', 'batman', 'beer', 'berlin', 'beryllium',
        'black', 'blossom', 'blue', 'bluebird', 'bravo', 'bulldog', 'burger',
        'butter', 'california', 'carbon', 'cardinal', 'carolina', 'carpet', 'cat',
        'ceiling', 'charlie', 'chicken', 'coffee', 'cola', 'cold', 'colorado',
        'comet', 'connecticut', 'crazy', 'cup', 'dakota', 'december', 'delaware',
        'delta', 'diet', 'don', 'double', 'early', 'earth', 'east', 'echo',
        'edward', 'eight', 'eighteen', 'eleven', 'emma', 'enemy', 'equal',
        'failed', 'fanta', 'fifteen', 'fillet', 'finch', 'fish', 'five', 'fix',
        'floor', 'florida', 'football', 'four', 'fourteen', 'foxtrot', 'freddie',
        'friend', 'fruit', 'gee', 'georgia', 'glucose', 'golf', 'green', 'grey',
        'hamper', 'happy', 'harry', 'hawaii', 'helium', 'high', 'hot', 'hotel',
        'hydrogen', 'idaho', 'illinois', 'india', 'indigo', 'ink', 'iowa',
        'island', 'item', 'jersey', 'jig', 'johnny', 'juliet', 'july', 'jupiter',
        'kansas', 'kentucky', 'kilo', 'king', 'kitten', 'lactose', 'lake', 'lamp',
        'lemon', 'leopard', 'lima', 'lion', 'lithium', 'london', 'louisiana',
        'low', 'magazine', 'magnesium', 'maine', 'mango', 'march', 'mars',
        'maryland', 'massachusetts', 'may', 'mexico', 'michigan', 'mike',
        'minnesota', 'mirror', 'mississippi', 'missouri', 'mobile', 'mockingbird',
        'monkey', 'montana', 'moon', 'mountain', 'muppet', 'music', 'nebraska',
        'neptune', 'network', 'nevada', 'nine', 'nineteen', 'nitrogen', 'north',
        'november', 'nuts', 'october', 'ohio', 'oklahoma', 'one', 'orange',
        'oranges', 'oregon', 'oscar', 'oven', 'oxygen', 'papa', 'paris', 'pasta',
        'pennsylvania', 'pip', 'pizza', 'pluto', 'potato', 'princess', 'purple',
        'quebec', 'queen', 'quiet', 'red', 'river', 'robert', 'robin', 'romeo',
        'rugby', 'sad', 'salami', 'saturn', 'september', 'seven', 'seventeen',
        'shade', 'sierra', 'single', 'sink', 'six', 'sixteen', 'skylark', 'snake',
        'social', 'sodium', 'solar', 'south', 'spaghetti', 'speaker', 'spring',
        'stairway', 'steak', 'stream', 'summer', 'sweet', 'table', 'tango', 'ten',
        'tennessee', 'tennis', 'texas', 'thirteen', 'three', 'timing', 'triple',
        'twelve', 'twenty', 'two', 'uncle', 'undress', 'uniform', 'uranus', 'utah',
        'vegan', 'venus', 'vermont', 'victor', 'video', 'violet', 'virginia',
        'washington', 'west', 'whiskey', 'white', 'william', 'winner', 'winter',
        'wisconsin', 'wolfram', 'wyoming', 'xray', 'yankee', 'yellow', 'zebra',
        'zulu'
    ]

    private final List<String> wordlist

    HumanHasher() {
        wordlist = DEFAULT_WORD_LIST
    }

    HumanHasher(List<String> wordList) {
        wordlist = wordList?.size() == 256 ? wordList : DEFAULT_WORD_LIST
    }

    String humanize(String digest, int numWords = 4, String separator = "-") {

        if(!digest.matches(/^[a-fA-F0-9]+$/)){
            throw new Error("'digest' must be hexadecimal characters only.");
        }

        // loop through characters, grabbing two at a time and turning them into
        // a single hex value
        List bytes = digest.toList().collate(2).collect {
            Integer.parseInt("${it.first()}${it.last() ?: '0'}", 16)
        }

        List compressedBytes = compress(bytes, numWords);

        List outputWords = compressedBytes.collect {
            this.wordlist[it]
        }

        return outputWords.join(separator)

    }

    /**
     * Compress a list of byte values to a fixed target length.
     *
     * @param bytes
     * @param target
     * @return
     */
    def compress(List<Integer> bytes, int target) {

        if(bytes.size() == target) {
            return bytes
        }

        // A smaller number of bytes will be zero-padded to a larger number as needed
        while(bytes.size() < target) {
            bytes << 0
        }

        def bytesLength = bytes.size()
        int segmentSize = Math.floor(bytesLength / target) as int

        // Split `bytes` into `target` segments.
        // XOR is used for compression.
        // Left-over bytes are caught in the last segment.
        List<List> segments = bytes.collate(segmentSize)
        while(segments.size() > target) { //segments.last().size() < segments.first().size()
            segments.pop().each {
                segments.last() << it
            }
        }
        assert segments.size() == target
        List<Integer> compressedSegments = segments.collect { List<Integer> seg ->
            //seg_num = Math.min(Math.floor(i/seg_size), target-1 );
            // use XOR bitwise operation to reduce the List of Integers to a single Integer
            return seg.inject(0) { Integer acc, Integer val ->
                acc ^ val
            }
        }

        assert(compressedSegments.size() == target)
        return compressedSegments
    }

//    def uuid(numWords, separator) {
//        digest = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c){
//            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
//            return v.toString(16);
//        });
//        return [this.humanize(digest, numWords, separator), digest];
//    }

}

