package com.betvictor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetvictorApplication.class)
public class BetvictorApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void challengeApplicationTest() {
		BetvictorApplication.main(new String[] {});
	}

}
