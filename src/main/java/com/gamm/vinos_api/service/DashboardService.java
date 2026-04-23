package com.gamm.vinos_api.service;

import com.gamm.vinos_api.dto.view.DashboardAdminDTO;
import com.gamm.vinos_api.dto.view.DashboardUserDTO;

public interface DashboardService {
 DashboardUserDTO getDashboardUser();

 DashboardAdminDTO getDashboardAdmin();
}
