package springBatchBDD;

import java.util.Date;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FetchFilesTasklet implements Tasklet {

	public static final String INVENTORY_DATE = "inventoryDate";

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext params)
			throws Exception {
		
		Date inventoryDate=(Date)params.getAttribute(INVENTORY_DATE);
		
		String batchName=(String)params.getAttribute("batchName");
		
		
		
		
		
		
		return null;
		
		
		
		
	}

}
