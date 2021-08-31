package uz.pdp.clickup.service;

import javax.servlet.http.HttpServletResponse;

public interface AttachmentService {
    void download(String fileName, HttpServletResponse response);
}
