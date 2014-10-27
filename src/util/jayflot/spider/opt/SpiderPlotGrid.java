package util.jayflot.spider.opt;
import br.blog.arruda.plot.opt.PlotGrid;

/**
 * PlotGrid com opções relativas ao pluging Spider (grafico de Radar/Spider)
 * @author arruda
 *
 */
public class SpiderPlotGrid extends PlotGrid{
	
	/**
	 * Radar or Spider
	 */
	private String mode;
	
	public SpiderPlotGrid(){
		this.mode = null;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
