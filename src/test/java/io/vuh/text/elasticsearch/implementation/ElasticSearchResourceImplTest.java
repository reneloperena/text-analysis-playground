package io.vuh.text.elasticsearch.implementation;

import java.lang.reflect.Field;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Tests functionality for {@link ElasticSearchResourceImpl}
 * 
 * @author Rene Loperena <rene@vuh.io>
 *
 */
@RunWith(EasyMockRunner.class)
public class ElasticSearchResourceImplTest {
	@TestSubject
	private ElasticSearchResourceImpl esResource = new ElasticSearchResourceImpl();
	
	@Mock
	private ElasticSearchManagerImpl elasticSearchManager;
	
	/**
	 * Injects Mocks using Reflection
	 */
	@Before
	public void setUp() {
		Field field;
		try {
			field = ElasticSearchResourceImpl.class.getDeclaredField("elasticSearchManager");
			field.setAccessible(true);
			field.set(esResource, elasticSearchManager);
			field.setAccessible(false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// This should not fail unless the declared field name is changed.
		}
	}
}
