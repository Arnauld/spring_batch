package springBatchBDD;

public class FileDaoTestImpl implements FileDao {

	@Override
	public void save(CpmFile file) {
		throw new RuntimeException("Erf");
	}
	

}
