package test

import spock.lang.Unroll

import grails.core.GrailsDomainClass
import grails.testing.mixin.integration.Integration


@Integration
class PaletteSpec extends ReflectionSpecification {

    private static final Set<String> EXPECTED_PROPERTIES =
            ['id', 'version', 'author', 'description', 'colors', 'tags', 'complementaries'] as Set

    private static final Set<String> EXPECTED_PERSISTENT_PROPERTIES =
            ['author', 'colors', 'tags'] as Set

    private static final Map<String, Class<?>> EXPECTED_ASSOCIATIONS =
            [colors: Color, tags: String, complementaries: String]

    GrailsDomainClass palette

    def setup() {
        palette = getDomainClassArtefact(Palette)
    }

    def "getProperties"() {
        expect:
        palette.properties.collect { it.name }.toSet() == EXPECTED_PROPERTIES
    }

    def "getPersistentProperties"() {
        expect:
        palette.persistentProperties.collect { it.name }.toSet() == EXPECTED_PERSISTENT_PROPERTIES
    }

    def "getAssociationMap"() {
        expect:
        palette.associationMap == EXPECTED_ASSOCIATIONS
    }

    @Unroll
    def "hasProperty '#name'"(String name) {
        expect:
        palette.hasProperty(name)

        where:
        name << EXPECTED_PROPERTIES
    }

    @Unroll
    def "property '#name': getType returns #type"(String name, Class<?> type) {
        expect:
        palette.getPropertyByName(name).type == type

        where:
        name          | type
        'id'          | Long
        'version'     | Long
        'author'      | String
        'description' | String
        'colors'      | List
        'tags'        | Set
    }

    @Unroll
    def "property '#name': isPersistent returns #persistent"(String name, boolean persistent) {
        expect:
        palette.getPropertyByName(name).persistent == persistent

        where:
        name          | persistent
        'id'          | true
        'version'     | true
        'author'      | true
        'description' | false
        'colors'      | true
        'tags'        | true
    }

    @Unroll
    def "property '#name': getTypePropertyName returns #typePropName"(String name, String typePropName) {
        expect:
        palette.getPropertyByName(name).typePropertyName == typePropName

        where:
        name          | typePropName
        'id'          | 'long'
        'version'     | 'long'
        'author'      | 'string'
        'description' | 'string'
        'colors'      | 'list'
        'tags'        | 'set'
    }

    @Unroll
    def "property '#name': getReferencedPropertyType returns #refPropType"(String name, Class<?> refPropType) {
        expect:
        palette.getPropertyByName(name).referencedPropertyType == refPropType

        where:
        name          | refPropType
        'id'          | Long
        'version'     | Long
        'author'      | String
        'description' | String
        'colors'      | Color
        'tags'        | String
    }

    @Unroll
    def "property '#name': isAssociation returns #association"(String name, boolean association) {
        expect:
        palette.getPropertyByName(name).association == association

        where:
        name          | association
        'id'          | false
        'version'     | false
        'author'      | false
        'description' | false
        'colors'      | false
        'tags'        | false
    }

    @Unroll
    def "property '#name': getReferencedDomainClass returns GrailsDomainClass for #clazz"(String name, Class<?> clazz) {
        expect:
        palette.getPropertyByName(name).referencedDomainClass == getDomainClassArtefact(clazz)

        where:
        name          | clazz
        'id'          | null
        'version'     | null
        'author'      | null
        'description' | null
        'colors'      | null
        'tags'        | null
    }

}
