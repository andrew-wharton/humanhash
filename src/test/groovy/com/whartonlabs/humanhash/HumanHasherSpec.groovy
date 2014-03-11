package com.whartonlabs.humanhash

class HumanHasherSpec extends spock.lang.Specification {

    def "Test for null arguments"() {

        given:
        def hasher = new HumanHasher()

        when:
        hasher.humanize(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Digest can not be null"

    }

    def "Test for empty string arguments"() {

        given:
        def hasher = new HumanHasher()

        when:
        hasher.humanize("")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Digest must be at least one character"

    }

    def "Test for illegal charatcers (non-hex)"() {

        given:
        def hasher = new HumanHasher()
        when:
        hasher.humanize("jkhlkk56657dg")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Digest must be hexadecimal characters only."
    }

    def "Test the basic, single argument humanize function"() {

        given:
        def hasher = new HumanHasher()
        def digest = "7528880a986c40e78c38115e640da2a1"

        expect:
        hasher.humanize(digest) == "three-georgia-xray-jig"

    }

    def "Test the custom length humanize function"() {

        given:
        def hasher = new HumanHasher()
        def digest = "7528880a986c40e78c38115e640da2a1"

        expect:
        hasher.humanize(digest, 6) == "high-mango-white-oregon-purple-charlie"

    }

    def "Test the custom length and custom separator humanize function"() {

        given:
        def hasher = new HumanHasher()
        def digest = "7528880a986c40e78c38115e640da2a1"

        expect:
        hasher.humanize(digest, 6, "_") == "high_mango_white_oregon_purple_charlie"

    }

    def "Test the custom word list constructor"() {

        given: "a custom word list"
        def words = (0..255).collect { "alfa" }

        and:
        def digest = "7528880a986c40e78c38115e640da2a1"

        when:
        def hasher = new HumanHasher(words)

        then: "the human hash to contain word from the custom list"
        hasher.humanize(digest) == "alfa-alfa-alfa-alfa"

    }

    def "Test compression function compresses or expands byte array to target size"() {

        expect: "Same number of bytes does not modify array"
        new HumanHasher().compress([93, 130, 244, 167, 180, 79], 6) == [93, 130, 244, 167, 180, 79]

        and: "One extra byte reduces correctly"
        new HumanHasher().compress([93, 130, 244, 167, 180, 79, 105], 6) == [93, 130, 244, 167, 180, 38]

        and: "More than one extra bytes reduces correctly"
        new HumanHasher().compress([93, 130, 244, 167, 180, 79, 105, 3], 6) == [93, 130, 244, 167, 180, 37]

        and: "Less number of bytes are padded with 0 bytes"
        new HumanHasher().compress([93, 130, 244, 167, 180, 79], 9) == [93, 130, 244, 167, 180, 79, 0, 0, 0]

    }

}