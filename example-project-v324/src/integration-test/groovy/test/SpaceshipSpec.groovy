package test

import spock.lang.Unroll

import grails.core.GrailsDomainClass
import grails.test.mixin.integration.Integration


@Integration
class SpaceshipSpec extends ReflectionSpecification {

    private static final Set<String> EXPECTED_PROPERTIES =
            ['id', 'version', 'name', 'captain', 'captainId', 'shipData'] as Set

    private static final Set<String> EXPECTED_PERSISTENT_PROPERTIES =
            ['name', 'captain', 'shipData'] as Set

    private static final Map<String, Class<?>> EXPECTED_ASSOCIATIONS = [:]

    GrailsDomainClass spaceship

    def setup() {
        spaceship = getDomainClassArtefact(Spaceship)
    }

    def "getProperties"() {
        expect:
        spaceship.properties.collect { it.name }.toSet() == EXPECTED_PROPERTIES
    }

    def "getPersistentProperties"() {
        expect:
        spaceship.persistentProperties.collect { it.name }.toSet() == EXPECTED_PERSISTENT_PROPERTIES
    }

    def "getAssociationMap"() {
        expect:
        spaceship.associationMap == EXPECTED_ASSOCIATIONS
    }

    @Unroll
    def "hasProperty '#name'"(String name) {
        expect:
        spaceship.hasProperty(name)

        where:
        name << EXPECTED_PROPERTIES
    }

    @Unroll
    def "property '#name': getType returns #type"(String name, Class<?> type) {
        expect:
        spaceship.getPropertyByName(name).type == type

        where:
        name       | type
        'id'       | Long
        'version'  | Long
        'name'     | String
        'captain'  | Person
        'shipData' | String
    }

    @Unroll
    def "property '#name': isPersistent returns #persistent"(String name, boolean persistent) {
        expect:
        spaceship.getPropertyByName(name).persistent == persistent

        where:
        name       | persistent
        'id'       | true
        'version'  | true
        'name'     | true
        'captain'  | true
        'shipData' | true
    }

    @Unroll
    def "property '#name': getTypePropertyName returns #typePropName"(String name, String typePropName) {
        expect:
        spaceship.getPropertyByName(name).typePropertyName == typePropName

        where:
        name       | typePropName
        'id'       | 'long'
        'version'  | 'long'
        'name'     | 'string'
        'captain'  | 'person'
        'shipData' | 'string'
    }

    @Unroll
    def "property '#name': getReferencedPropertyType returns #refPropType"(String name, Class<?> refPropType) {
        expect:
        spaceship.getPropertyByName(name).referencedPropertyType == refPropType

        where:
        name       | refPropType
        'id'       | Long
        'version'  | Long
        'name'     | String
        'captain'  | Person
        'shipData' | String
    }

    @Unroll
    def "property '#name': isAssociation returns #association"(String name, boolean association) {
        expect:
        spaceship.getPropertyByName(name).association == association

        where:
        name       | association
        'id'       | false
        'version'  | false
        'name'     | false
        'captain'  | true
        'shipData' | false
    }

    @Unroll
    def "property '#name': getReferencedDomainClass returns GrailsDomainClass for #clazz"(String name, Class<?> clazz) {
        expect:
        spaceship.getPropertyByName(name).referencedDomainClass == getDomainClassArtefact(clazz)

        where:
        name       | clazz
        'id'       | null
        'version'  | null
        'name'     | null
        'captain'  | Person
        'shipData' | null
    }

}
