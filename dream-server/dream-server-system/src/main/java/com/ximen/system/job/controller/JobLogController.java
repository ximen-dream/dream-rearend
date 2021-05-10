package com.ximen.system.job.controller;

import com.wuwenze.poi.ExcelKit;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.entity.constant.StringConstant;
import com.ximen.common.core.entity.job.JobLog;
import com.ximen.common.core.utils.DreamUtil;
import com.ximen.system.job.service.IJobLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("log")
@RequiredArgsConstructor
public class JobLogController {

    private final IJobLogService jobLogService;

    @GetMapping
    @PreAuthorize("hasAuthority('job:log:view')")
    public DreamResponse jobLogList(QueryRequest request, JobLog log) {
        Map<String, Object> dataTable = DreamUtil.getDataTable(this.jobLogService.findJobLogs(request, log));
        return new DreamResponse().data(dataTable);
    }

    @DeleteMapping("{jobIds}")
    @PreAuthorize("hasAuthority('job:log:delete')")
    public void deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringConstant.COMMA);
        this.jobLogService.deleteJobLogs(ids);
    }

    @GetMapping("excel")
    @PreAuthorize("hasAuthority('job:log:export')")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
        ExcelKit.$Export(JobLog.class, response).downXlsx(jobLogs, false);
    }
}
