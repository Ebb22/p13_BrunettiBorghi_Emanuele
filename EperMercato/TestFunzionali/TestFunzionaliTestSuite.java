import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({
	UC1_Login.class,
	UC2_Logout.class,
	UC37_InserireNegozio.class,
	UC38_RicercareNegozio.class,
	UC39_ModificareNegozio.class,
	UC40_EliminareNegozio.class,
	UC41_InserireScontoGenerale.class,
	UC42_EliminareScontoGenerale.class,
	UC43_InserirePremio.class,
	UC44_EliminarePremio.class,
	UC45_ApprovareRecensione.class
})

public class TestFunzionaliTestSuite {
}
