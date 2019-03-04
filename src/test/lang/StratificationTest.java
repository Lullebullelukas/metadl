package lang;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import beaver.Parser.Exception;
import lang.ast.Program;
import lang.ast.config.Description;
import lang.io.FileUtil;
import lang.io.SimpleLogger;
import lang.relation.Stratification;
import lang.relation.Stratification.Stratum;

public class StratificationTest {

	@ParameterizedTest
	@MethodSource("testStratProvider")
	public void testStrat(String fn, String expected) throws IOException, Exception {
		Description d1 = FileUtil.parseDescription("eval::bottomupnaive ./tests/stratification/" + fn);
		Program program1 = FileUtil.parse(new File(d1.getInput().getPath()));
		Stratification stratification = new Stratification(program1);
		Deque<Stratum> order = stratification.order();
		SimpleLogger.logger().log("EXPECTED: " + expected, SimpleLogger.LogLevel.Level.DEBUG).log(order.toString());
		assertTrue(expected.equals(order.toString()));
	}

	static Stream<Arguments> testStratProvider() {
		return Stream.of(Arguments.of("strat_1.in", "[[FP_C], [FP_A, FP_B]]"),
				Arguments.of("strat_2.in", "[[FP_C], [FP_F], [FP_A, FP_B, FP_D, FP_E]]"));
	}

	@Test
	public void testStratTwice() throws IOException, beaver.Parser.Exception {
		Description d1 = FileUtil.parseDescription("eval::bottomupnaive ./tests/stratification/strat_1.in");
		Program program1 = FileUtil.parse(new File(d1.getInput().getPath()));
		Stratification stratification = new Stratification(program1);
		Deque<Stratum> order = stratification.order();
		String string_order_1 = order.toString();
		Stratification stratification1 = new Stratification(program1);
		order = stratification1.order();
		assertTrue(string_order_1.equals(order.toString()));
	}
}
