package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.DashboardAdminView;
import com.gamm.vinos_api.dto.view.DashboardUserView;

public interface DashboardService {
 DashboardUserView getDashboardUser();

 DashboardAdminView getDashboardAdmin();
}
