package com.whartonlabs.humanhash

class HumanHasherSpec extends spock.lang.Specification {

    def "Test the compression function"() {

        given:
        def humanhasher = new HumanHasher()

        expect:
        humanhasher.compress([34,5,3,2,5,6,8,3,4,7,2,4,5], 3) == [38, 8, 0]

        humanhasher.compress([93, 130, 244, 167, 180, 79, 105, 3], 6) == [93, 130, 244, 167, 180, 37]

    }

    def "Test the humanize function"() {

        given:
        def humanhasher = new HumanHasher()
        def digest = "7528880a986c40e78c38115e640da2a1"

        expect:
        humanhasher.humanize(digest) == "three-georgia-xray-jig"
        humanhasher.humanize(digest, 6) == "high-mango-white-oregon-purple-charlie"

    }

}