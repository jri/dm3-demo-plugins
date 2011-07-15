package de.deepamehta.test;

import de.deepamehta.core.Topic;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.model.TopicModel;
import de.deepamehta.core.service.DeepaMehtaService;

import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.autoWrap;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.cleanCaches;
import static org.ops4j.pax.exam.container.def.PaxRunnerOptions.scanPom;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import java.util.logging.Logger;



@RunWith(JUnit4TestRunner.class)
public class PluginTest {

    @Inject
    private BundleContext bundleContext;

    private DeepaMehtaService dms;
    private Logger logger = Logger.getLogger(getClass().getName());

    @Before
    public void setup() throws Exception {
        dms = retrieveCoreService();
    }

    @Configuration
    public static Option[] configuration() {
        return options(felix(), cleanCaches(), autoWrap(),
            systemProperty("org.osgi.service.http.port").value("8086"),
            systemProperty("dm3.database.path").value("dm3-db"),
            scanPom("mvn:de.deepamehta/felix-bundles/0.5-SNAPSHOT/pom"),
            scanPom("mvn:de.deepamehta/3rd-party-bundles/0.5-SNAPSHOT/pom"),
            scanPom("mvn:de.deepamehta/deepamehta3-bundles/0.5-SNAPSHOT/pom"),
            scanPom("mvn:de.deepamehta.demo-plugins/dm3-demo-plugin-1/0.5-SNAPSHOT/pom"));
    }

    @Test
    public void test() {
        String TXT = "Hi Demo!";
        Topic t1 = dms.createTopic(new TopicModel("domain.dm3_demo_plugins.my_topic_type", new SimpleValue(TXT)), null);
        assertEquals(TXT, t1.getSimpleValue().toString());
        Topic t2 = dms.getTopic(t1.getId(), false, null);
        assertEquals(TXT, t2.getSimpleValue().toString());
    }

    // ------------------------------------------------------------------------------------------------- Private Methods

    private DeepaMehtaService retrieveCoreService() throws InterruptedException {
        ServiceTracker tracker = new ServiceTracker(bundleContext, DeepaMehtaService.class.getName(), null);
        tracker.open();
        DeepaMehtaService dms = (DeepaMehtaService) tracker.waitForService(5000);
        tracker.close();
        assertNotNull(dms);
        return dms;
    }
}
