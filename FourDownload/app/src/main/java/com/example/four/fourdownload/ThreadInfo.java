package com.example.four.fourdownload;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class ThreadInfo {
    private int id;
    private String url;
    private long start;
    private long end;
    private long finished;
    private long fileLength;


    /**
     * @param id       针对这一url的线程的id
     * @param url
     * @param start
     * @param end
     * @param finished
     */
    public ThreadInfo(int id, String url, long start, long end, long finished, long fileLength) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
        this.fileLength = fileLength;
    }

    public ThreadInfo() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    @Override
    public String toString() {
        return "ThreadInfo [id=" + id + ", url=" + url + ", start=" + start + ", end=" + end + ", finished=" +
                finished + ", fileLength=" + fileLength + "]";
    }

}
