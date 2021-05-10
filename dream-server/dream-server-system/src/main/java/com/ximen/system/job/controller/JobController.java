package com.ximen.system.job.controller;

import com.wuwenze.poi.ExcelKit;
import com.ximen.common.core.code.ResponseCode;
import com.ximen.common.core.dto.response.PageResultDTO;
import com.ximen.common.core.dto.response.ResultDTO;
import com.ximen.common.core.entity.DreamResponse;
import com.ximen.common.core.entity.QueryRequest;
import com.ximen.common.core.entity.constant.StringConstant;
import com.ximen.common.core.entity.job.Job;
import com.ximen.common.core.exception.DreamException;
import com.ximen.common.core.utils.DreamUtil;
import com.ximen.system.job.service.IJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("job")
public class JobController {

    private final IJobService jobService;

    @GetMapping("page/{pageNumber}/{pageSize}")
//    @PreAuthorize("hasAuthority('job:view')")
    public ResultDTO jobList(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,String searchKey) {
        PageResultDTO<Job> jobPageRes = this.jobService.page(pageNumber,pageSize,searchKey);
        return ResultDTO.ok(jobPageRes);
    }

    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('job:add')")
    public ResultDTO addJob(@Valid @RequestBody Job job) throws DreamException {
        boolean validExpression = CronExpression.isValidExpression(job.getCronExpression());
        if(!validExpression) throw new DreamException(ResponseCode.ERROR_CRON_EXPRESSION);
        this.jobService.createJob(job);
        return ResultDTO.ok();
    }

    @DeleteMapping("{jobIds}")
//    @PreAuthorize("hasAuthority('job:delete')")
    public void deleteJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        String[] ids = jobIds.split(StringConstant.COMMA);
        this.jobService.deleteJobs(ids);
    }

    @PutMapping("{id}")
//    @PreAuthorize("hasAuthority('job:update')")
    public void updateJob(@Valid @RequestBody Job job,@PathVariable Long id) {
        job.setJobId(id);
        this.jobService.updateJob(job);
    }

    @GetMapping("run/{jobIds}")
//    @PreAuthorize("hasAuthority('job:run')")
    public void runJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.run(jobIds);
    }

    @GetMapping("pause/{jobIds}")
//    @PreAuthorize("hasAuthority('job:pause')")
    public void pauseJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.pause(jobIds);
    }

    @GetMapping("resume/{jobIds}")
//    @PreAuthorize("hasAuthority('job:resume')")
    public void resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
    }

//    @PostMapping("excel")
//    @PreAuthorize("hasAuthority('job:export')")
//    public void export(QueryRequest request, Job job, HttpServletResponse response) {
//        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
//        ExcelKit.$Export(Job.class, response).downXlsx(jobs, false);
//    }
}
