package com.whartonlabs.humanhash

class HumanHasherSpec extends spock.lang.Specification {

    def "Test the compression function"() {

        given:
        def hasher = new HumanHasher()

        expect:
        hasher.compress([34,5,3,2,5,6,8,3,4,7,2,4,5], 3) == [38, 8, 0]

        and:
        hasher.compress([93, 130, 244, 167, 180, 79, 105, 3], 6) == [93, 130, 244, 167, 180, 37]

    }

    def "Test the humanize function"() {

        given:
        def hasher = new HumanHasher()
        def digest = "7528880a986c40e78c38115e640da2a1"

        expect:
        hasher.humanize(digest) == "three-georgia-xray-jig"
        hasher.humanize(digest, 6) == "high-mango-white-oregon-purple-charlie"

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

}