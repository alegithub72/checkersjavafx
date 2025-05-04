package sa.fx.draugths.animation;

import java.util.List;

public class FrameSequence {

    private FrameInfo[] seqList;
    private long seqDuration;

    public FrameSequence(FrameInfo[] seqList) {
        this.seqList = seqList;
        seqDuration=1500;
    }

    public FrameInfo[] getSeqList() {
        return seqList;
    }

    public void setSeqList(FrameInfo[] seqList) {
        this.seqList = seqList;
    }

    public long getSeqDuration() {
        return seqDuration;
    }

    public void setSeqDuration(long seqDuration) {
        this.seqDuration = seqDuration;
    }
}
