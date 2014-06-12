package springBatchBDD;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * test to make sure that constant names are what we expect : if it fails, 
 * double check the impact on .bat files, in which constant names are "hardcoded" and may not match 
 * anymore with the constant declaration 
 *  *
 */
public class ConstantsTest {

	@Test
	public void constants_have_expected_value(){
		assertThat(FetchFilesTasklet.INVENTORY_DATE).isEqualTo("inventoryDate");
		assertThat(FetchFilesTasklet.BATCH_NAME).isEqualTo("batchName");
		
	}
	
}
