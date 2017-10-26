package test

import spock.lang.Specification

import grails.core.GrailsApplication
import grails.core.GrailsDomainClass
import org.grails.core.artefact.DomainClassArtefactHandler

import org.springframework.beans.factory.annotation.Autowired


class ReflectionSpecification extends Specification {

    @Autowired
    GrailsApplication grailsApplication

    GrailsDomainClass getDomainClassArtefact(Class<?> clazz) {
        if (!clazz) return null

        (GrailsDomainClass) this.grailsApplication.getArtefact(DomainClassArtefactHandler.TYPE, clazz.canonicalName)
    }

}
