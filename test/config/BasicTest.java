package config;
import org.junit.Before;

import play.test.Fixtures;
import play.test.UnitTest;

public class BasicTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
	}
}
