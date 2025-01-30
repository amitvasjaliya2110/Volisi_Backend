package com.volisi.service;

import com.volisi.dto.request.GlobarSearchRequest;
import com.volisi.dto.response.QuizLibraryResponseRecord;
import java.util.List;

public interface DashboardService {
  List<QuizLibraryResponseRecord> filter(GlobarSearchRequest request);
}
