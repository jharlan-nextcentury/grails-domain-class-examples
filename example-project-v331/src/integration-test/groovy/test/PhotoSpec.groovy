package test

import spock.lang.Unroll

import grails.core.GrailsDomainClass
import grails.testing.mixin.integration.Integration


@Integration
class PhotoSpec extends ReflectionSpecification {

    private static final Set<String> EXPECTED_PROPERTIES =
            ['id', 'version', 'name', 'url'] as Set

    private static final Set<String> EXPECTED_PERSISTENT_PROPERTIES =
            ['name', 'url'] as Set

    private static final Map<String, Class<?>> EXPECTED_ASSOCIATIONS = [:]

    GrailsDomainClass photo

    def setup() {
        photo = getDomainClassArtefact(Photo)
    }

    def "getProperties"() {
        expect:
        photo.properties.collect { it.name }.toSet() == EXPECTED_PROPERTIES
    }

    def "getPersistentProperties"() {
        expect:
        photo.persistentProperties.collect { it.name }.toSet() == EXPECTED_PERSISTENT_PROPERTIES
    }

    def "getAssociationMap"() {
        expect:
        photo.associationMap == EXPECTED_ASSOCIATIONS
    }

    @Unroll
    def "hasProperty '#name'"(String name) {
        expect:
        photo.hasProperty(name)

        where:
        name << EXPECTED_PROPERTIES
    }

    @Unroll
    def "property '#name': getType returns #type"(String name, Class<?> type) {
        expect:
        photo.getPropertyByName(name).type == type

        where:
        name      | type
        'id'      | Long
        'version' | Long
        'name'    | String
        'url'     | String
    }

    @Unroll
    def "property '#name': isPersistent returns #persistent"(String name, boolean persistent) {
        expect:
        photo.getPropertyByName(name).persistent == persistent

        where:
        name      | persistent
        'id'      | true
        'version' | true
        'name'    | true
        'url'     | true
    }

    @Unroll
    def "property '#name': getTypePropertyName returns #typePropName"(String name, String typePropName) {
        expect:
        photo.getPropertyByName(name).typePropertyName == typePropName

        where:
        name      | typePropName
        'id'      | 'long'
        'version' | 'long'
        'name'    | 'string'
        'url'     | 'string'
    }

    @Unroll
    def "property '#name': getReferencedPropertyType returns #refPropType"(String name, Class<?> refPropType) {
        expect:
        photo.getPropertyByName(name).referencedPropertyType == refPropType

        where:
        name      | refPropType
        'id'      | Long
        'version' | Long
        'name'    | String
        'url'     | String
    }

    @Unroll
    def "property '#name': isAssociation returns #association"(String name, boolean association) {
        expect:
        photo.getPropertyByName(name).association == association

        where:
        name      | association
        'id'      | false
        'version' | false
        'name'    | false
        'url'     | false
    }

    @Unroll
    def "property '#name': getReferencedDomainClass returns GrailsDomainClass for #clazz"(String name, Class<?> clazz) {
        expect:
        photo.getPropertyByName(name).referencedDomainClass == getDomainClassArtefact(clazz)

        where:
        name      | clazz
        'id'      | null
        'version' | null
        'name'    | null
        'url'     | null
    }

}
