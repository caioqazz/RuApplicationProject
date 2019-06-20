package br.ufop.ruapplicationpassivemvc.model.response;

import java.util.ArrayList;
import java.util.List;

import br.ufop.ruapplicationpassivemvc.model.entity.Demand;
import br.ufop.ruapplicationpassivemvc.model.entity.DemandReport;

public class ReportDemandResponse {
   private List<DemandReport> demands;

    public List<DemandReport> getDemands() {
        return demands;
    }

    public void setDemands(List<DemandReport> demands) {
        this.demands = demands;
    }
}
