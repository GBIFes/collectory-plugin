import grails.build.logging.GrailsConsole
import grails.util.Environment

class CollectoryGrailsPlugin {
    def grailsApplication
    def dataLoaderService
    def authenticateService

    // the plugin version
    def version = "1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Collectory Plugin" // Headline display name of the plugin
    def author = "Alan Lin"
    def authorEmail = "alan.lin@csiro.au"
    def description = '''\
A Grails plugin to provide the core functionality for collection and displaying biodiversity data from
collectory web services. Data access is via JSON REST web services
from the ALA collectory app (no local DB is required for this app).
'''

    // URL to the plugin's documentation
    def documentation = "http://github.com/AtlasOfLivingAustralia/collectory"

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "MPL2"

    // Details of company behind the plugin (if there is one)
    def organization = [ name: "Atlas of Living Australia", url: "http://www.ala.org.au/" ]

    // Any additional developers beyond the author specified above.
    def developers = [
            [ name: "Dave Martin", email: "david.martin@csiro.au" ],
            [ name: "Dave Baird", email: "david.baird@csiro.au" ]
    ]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "Google Code", url: "https://github.com/AtlasOfLivingAustralia/collectory/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/AtlasOfLivingAustralia/collectory" ]

    def grailsConsole =  new GrailsConsole()

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        def config = application.config

        // EhCache settings
        if (!config.grails.cache.config) {
            config.grails.cache.config = {
                defaults {1
                    eternal false
                    overflowToDisk false
                    maxElementsInMemory 10000
                    timeToLiveSeconds 3600
                }
                cache {
                    name 'longTermCache'
                    timeToLiveSeconds (3600 * 12)
                }
            }
        }

        // Apache proxyPass & cached-resources seems to mangle image URLs in plugins, so we exclude caching it
        application.config.grails.resources.mappers.hashandcache.excludes = ["**/images/*.*"]

        def loadConfig = new ConfigSlurper(Environment.current.name).parse(application.classLoader.loadClass("defaultConfig"))
        application.config = loadConfig.merge(config) // client app will now override the defaultConfig version
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = {ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}