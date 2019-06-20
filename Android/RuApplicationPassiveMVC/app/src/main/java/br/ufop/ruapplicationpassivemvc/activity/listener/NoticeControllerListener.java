package br.ufop.ruapplicationpassivemvc.activity.listener;

import br.ufop.ruapplicationpassivemvc.model.entity.Notice;

public interface NoticeControllerListener {
    void newNotice();

    void noticeDetail(Notice notice);

    void onActionExecuted(String s, int result);

    void noticeEdit(Notice item);
}
