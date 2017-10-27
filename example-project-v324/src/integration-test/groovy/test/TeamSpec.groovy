package test

import spock.lang.Unroll

import grails.core.GrailsDomainClass
import grails.test.mixin.integration.Integration


@Integration
class TeamSpec extends ReflectionSpecification {

    private static final Set<String> EXPECTED_PROPERTIES =
            ['id', 'version', 'name', 'strip', 'players', 'fans'] as Set

    private static final Set<String> EXPECTED_PERSISTENT_PROPERTIES =
            ['name', 'strip'] as Set

    private static final Map<String, Class<?>> EXPECTED_ASSOCIATIONS =
            [players: Player, fans: Fan]

    GrailsDomainClass team

    def setup() {
        team = getDomainClassArtefact(Team)
    }

    def "getProperties"() {
        expect:
        team.properties.collect { it.name }.toSet() == EXPECTED_PROPERTIES
    }

    def "getPersistentProperties"() {
        expect:
        team.persistentProperties.collect { it.name }.toSet() == EXPECTED_PERSISTENT_PROPERTIES
    }

    def "getAssociationMap"() {
        expect:
        team.associationMap == EXPECTED_ASSOCIATIONS
    }

    @Unroll
    def "hasProperty '#name'"(String name) {
        expect:
        team.hasProperty(name)

        where:
        name << EXPECTED_PROPERTIES
    }

    @Unroll
    def "property '#name': getType returns #type"(String name, Class<?> type) {
        expect:
        team.getPropertyByName(name).type == type

        where:
        name      | type
        'id'      | Long
        'version' | Long
        'name'    | String
        'strip'   | String
        'players' | Object
        'fans'    | Object
    }

    @Unroll
    def "property '#name': isPersistent returns #persistent"(String name, boolean persistent) {
        expect:
        team.getPropertyByName(name).persistent == persistent

        where:
        name      | persistent
        'id'      | true
        'version' | true
        'name'    | true
        'strip'   | true
        'players' | false
        'fans'    | false
    }

    @Unroll
    def "property '#name': getTypePropertyName returns #typePropName"(String name, String typePropName) {
        expect:
        team.getPropertyByName(name).typePropertyName == typePropName

        where:
        name      | typePropName
        'id'      | 'long'
        'version' | 'long'
        'name'    | 'string'
        'strip'   | 'string'
        'players' | 'object'
        'fans'    | 'object'
    }

    @Unroll
    def "property '#name': getReferencedPropertyType returns #refPropType"(String name, Class<?> refPropType) {
        expect:
        team.getPropertyByName(name).referencedPropertyType == refPropType

        where:
        name      | refPropType
        'id'      | Long
        'version' | Long
        'name'    | String
        'strip'   | String
        'players' | Object
        'fans'    | Object
    }

    @Unroll
    def "property '#name': isAssociation returns #association"(String name, boolean association) {
        expect:
        team.getPropertyByName(name).association == association

        where:
        name      | association
        'id'      | false
        'version' | false
        'name'    | false
        'strip'   | false
        'players' | false
        'fans'    | false
    }

    @Unroll
    def "property '#name': getReferencedDomainClass returns GrailsDomainClass for #clazz"(String name, Class<?> clazz) {
        expect:
        team.getPropertyByName(name).referencedDomainClass == getDomainClassArtefact(clazz)

        where:
        name      | clazz
        'id'      | null
        'version' | null
        'name'    | null
        'strip'   | null
        'players' | null
        'fans'    | null
    }

    @Unroll
    def "property '#name': getRelatedClassType returns #clazz"(String name, Class<?> clazz) {
        expect:
        team.getRelatedClassType(name) == clazz

        where:
        name      | clazz
        'id'      | null
        'version' | null
        'name'    | null
        'strip'   | null
        'players' | Player
        'fans'    | Fan
    }

}
