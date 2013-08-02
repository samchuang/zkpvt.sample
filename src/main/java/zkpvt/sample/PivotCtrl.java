package zkpvt.sample;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.zkoss.pivot.PivotField;
import org.zkoss.pivot.Pivottable;
import org.zkoss.pivot.impl.TabularPivotModel;
import org.zkoss.pivot.util.Exports;
import org.zkoss.pivot.util.Exports.PivotExportContext;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;

public class PivotCtrl extends SelectorComposer<Component> {

	@Wire
	Pivottable pivottable;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		TabularPivotModel model = getModel();
		model.setFieldType("Airline", PivotField.Type.COLUMN);
		model.setFieldType("Agent", PivotField.Type.ROW);
		model.setFieldType("Price", PivotField.Type.DATA);
		
		pivottable.setModel(model);
	}
	
	@Listen("onClick=#exportBtn")
	public void exportToExcel() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PivotExportContext context = Exports.getExportContext(pivottable, false, null);
		Exports.exportExcel(out, "xlsx", context, null);
		Filedownload.save(out.toByteArray(), "application/vnd.ms-excel", "pivot.xlsx"); // file download
		try {
		    out.close();
		} catch (IOException e) {}
	}
	
	public static TabularPivotModel getModel() {
	    return new TabularPivotModel(getData(), getColumns());
	}
	
	private static final long TODAY = new Date().getTime();
	private static final long DAY = 1000 * 60 * 60 * 24;
	private static Date dt(int i){
	    return new Date(TODAY + i * DAY);
	}
	// raw data
	public static List<List<Object>> getData() {
	    Object[][] objs = new Object[][] {
	            { "Carlene Valone", "Tameka Meserve",    "ATB Air", "AT15",  dt(-7), "Berlin",     "Paris",     186.6, 545  },
	            { "Antonio Mattos", "Sharon Roundy",     "Jasper",  "JS1",   dt(-5), "Frankfurt",  "Berlin",    139.5, 262  },
	            { "Russell Testa",  "Carl Whitmore",     "Epsilon", "EP2",   dt(-3), "Dublin",     "London",    108.0, 287  },
	            { "Antonio Mattos", "Velma Sutherland",  "Epsilon", "EP5",   dt(-1), "Berlin",     "London",    133.5, 578  },
	            { "Carlene Valone", "Cora Million",      "Jasper",  "JS30",  dt(-4), "Paris",      "Frankfurt", 175.4, 297  },
	            { "Richard Hung",   "Candace Marek",     "DTB Air", "BK201", dt(-5), "Manchester", "Paris",     168.5, 376  },
	            { "Antonio Mattos", "Albert Briseno",    "Fujito",  "FJ1",   dt(-7), "Berlin",     "Osaka",     886.9, 5486 },
	            { "Russell Testa",  "Louise Knutson",    "HST Air", "HT6",   dt(-2), "Prague",     "London",    240.6, 643  },
	            { "Antonio Mattos", "Jessica Lunsford",  "Jasper",  "JS9",   dt(-4), "Munich",     "Lisbon",    431.6, 1222 },
	            // more rows...
	            { "Russell Testa",  "Velma Sutherland",  "Epsilon", "EP4",   dt(-7), "London",     "Berlin",    155.7, 578  }
	    };
	     
	    List<List<Object>> list = new ArrayList<List<Object>>();
	    for(Object[] a : objs)
	        list.add(Arrays.asList(a));
	    return list;
	}
	 
	// column labels
	public static List<String> getColumns() {
	    return Arrays.asList(new String[]{
	            "Agent", "Customer", "Airline", "Flight", "Date", "Origin", "Destination", "Price", "Mileage"
	    });
	}
}
