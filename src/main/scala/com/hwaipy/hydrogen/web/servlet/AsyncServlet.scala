package com.hwaipy.hydrogen.web.servlet

import java.util.concurrent.Executors
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import scala.concurrent.Future

object AsyncServlet {
  val executionContext = concurrent.ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
}

class AsyncHttpServlet extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doGetAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doGetAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doGet(req, resp)
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doPostAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doPostAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doPost(req, resp)
  }

  override def doDelete(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doDeleteAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doDeleteAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doDelete(req, resp)
  }

  override def doHead(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doHeadAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doHeadAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doHead(req, resp)
  }

  override def doOptions(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doOptionsAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doOptionsAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doOptions(req, resp)
  }

  override def doPut(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doPutAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doPutAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doPut(req, resp)
  }

  override def doTrace(req: HttpServletRequest, resp: HttpServletResponse) {
    val ctx = req.startAsync()
    Future {
      doTraceAsync(req, resp)
      ctx.complete()
    }(AsyncServlet.executionContext)
  }

  def doTraceAsync(req: HttpServletRequest, resp: HttpServletResponse) {
    super.doTrace(req, resp)
  }
}
