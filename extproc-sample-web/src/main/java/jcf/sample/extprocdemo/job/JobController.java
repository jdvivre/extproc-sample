/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jcf.sample.extprocdemo.job;

import java.util.ArrayList;
import java.util.List;

import jcf.extproc.ExternalProcessOperator;
import jcf.extproc.process.ExternalProcess;
import jcf.extproc.process.JobInstanceFilter;
import jcf.extproc.process.JobInstanceInfo;
import jcf.extproc.spring.ExternalProcessOperatorFactory;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UI Controller for Event actions.
 * @author Keith Donald
 */
@Controller
public class JobController {
	
	// for web service (JSON) clients

	@Autowired
	private ExternalProcessOperator operator;
	
//	/**
//	 * Write the list of upcoming events to the body of the response.
//	 * Only matches 'GET /events' requests for JSON content; a 404 is sent otherwise.
//	 * TODO send a 406 if an unsupported representation, such as XML, is requested.  See SPR-7353.
//	 */
//	@RequestMapping(value="/events", method=RequestMethod.GET, produces="application/json") 
//	public @ResponseBody List<ExternalProcess> upcomingEvents(@RequestParam(value="after", required=false) @DateTimeFormat(iso=ISO.DATE_TIME) Long afterMillis) {
//		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
//		for (String jobName : operator.getJobNames()) {
//			list.add(operator.getJob(jobName));
//		}
//		return list;
//	}
//
//	/**
//	 * Write the list of event favorites to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/favorites", method=RequestMethod.GET, produces="application/json")
//	public @ResponseBody List<EventSession> favorites(@PathVariable Long eventId, Account account) {
//		return eventRepository.findEventFavorites(eventId, account.getId());
//	}
//
//	/**
//	 * Write a page of event tweet search results to the body of the response.
//	 * The page number and size may be provided by the client.  If not specified, defaults to the first page of ten results.
//	 */
//	@RequestMapping(value="/events/{eventId}/tweets", method=RequestMethod.GET, produces="application/json")
//	public @ResponseBody SearchResults tweets(@PathVariable Long eventId,  @RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="10") Integer pageSize) {
//		String searchString = eventRepository.findEventSearchString(eventId);
//		return searchString != null && searchString.length() > 0 ? twitter.searchOperations().search(searchString, page, pageSize) : null;
//	}
//
//	/**
//	 * Post a tweet about the event to Twitter.
//	 * Write OK status back if this is successful.
//	 */
//	@RequestMapping(value="/events/{eventId}/tweets", method=RequestMethod.POST)
//	public ResponseEntity<String> postTweet(@PathVariable Long eventId, @RequestParam String status, Location currentLocation) {
//		twitter.timelineOperations().updateStatus(status);
//		return new ResponseEntity<String>(HttpStatus.OK);
//	}
//	
//	/**
//	 * Retweet another event tweet.
//	 */
//	@RequestMapping(value="/events/{eventId}/retweet", method=RequestMethod.POST)
//	public @ResponseBody ResponseEntity<String> postRetweet(@PathVariable Long eventId, @RequestParam Long tweetId) {
//		twitter.timelineOperations().retweet(tweetId);
//		return new ResponseEntity<String>(HttpStatus.OK);
//	}
//
//	/**
//	 * Write the attendee's list of favorite sessions to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/favorites", method=RequestMethod.GET, produces="application/json")
//	public @ResponseBody List<EventSession> favoriteSessions(@PathVariable Long eventId, Account account) {
//		return eventRepository.findAttendeeFavorites(eventId, account.getId());
//	}
//
//	/**
//	 * Write the sessions scheduled for the day to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{day}", method=RequestMethod.GET, produces="application/json")
//	public @ResponseBody List<EventSession> sessionsOnDay(@PathVariable Long eventId, @PathVariable @DateTimeFormat(iso=ISO.DATE) LocalDate day, Account account) {
//		return eventRepository.findSessionsOnDay(eventId, day, account.getId());
//	}
//
//	/**
//	 * Toggle a session as an attendee favorite.
//	 * Write the new favorite status to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{sessionId}/favorite", method=RequestMethod.PUT)
//	public @ResponseBody Boolean toggleFavorite(@PathVariable Long eventId, @PathVariable Integer sessionId, Account account) {
//		return eventRepository.toggleFavorite(eventId, sessionId, account.getId());
//	}
//
//	/**
//	 * Add or update the rating given to the session by the attendee.
//	 * Write the new average rating for the session to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{sessionId}/rating", method=RequestMethod.POST)
//	public @ResponseBody Float updateRating(@PathVariable Long eventId, @PathVariable Integer sessionId, Account account, @RequestParam Short value, @RequestParam String comment) throws RatingPeriodClosedException {
//		return eventRepository.rate(eventId, sessionId, account.getId(), new Rating(value, comment));
//	}
//
//	/**
//	 * Write a page of session tweet search results to the body of the response.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{sessionId}/tweets", method=RequestMethod.GET, produces="application/json")
//	public @ResponseBody SearchResults sessionTweets(@PathVariable Long eventId, @PathVariable Integer sessionId, @RequestParam(defaultValue="1") Integer page, @RequestParam(defaultValue="10") Integer pageSize) {
//		String searchString = eventRepository.findSessionSearchString(eventId, sessionId);
//		return searchString != null && searchString.length() > 0 ? twitter.searchOperations().search(searchString, page, pageSize) : null;
//	}
//
//	/**
//	 * Post a tweet about a session.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{sessionId}/tweets", method=RequestMethod.POST)
//	public ResponseEntity<String> postSessionTweet(@PathVariable Long eventId, @PathVariable Integer sessionId, @RequestParam String status, Location currentLocation) {
//		twitter.timelineOperations().updateStatus(status);
//		return new ResponseEntity<String>(HttpStatus.OK);
//	}
//	
//	/**
//	 * Retweet a session tweet.
//	 */
//	@RequestMapping(value="/events/{eventId}/sessions/{sessionId}/retweet", method=RequestMethod.POST)
//	public @ResponseBody ResponseEntity<String> postSessionRetweet(@PathVariable Long eventId, @PathVariable Integer sessionId, @RequestParam Long tweetId) {
//		twitter.timelineOperations().retweet(tweetId);
//		return new ResponseEntity<String>(HttpStatus.OK);
//	}
//	
	// for web browser (HTML) clients

	/**
	 * Render the list of upcoming events as HTML in the client's web browser. 
	 */
	@RequestMapping(value="/jobs", method=RequestMethod.GET)
	public String list(Model model) {
		List<ExternalProcess> list = new ArrayList<ExternalProcess>();
		for (String jobName : operator.getJobNames()) {
			list.add(operator.getJob(jobName));
		}
		
		model.addAttribute("jobList", list);
		return "jobs/list";
	}
	
	@RequestMapping(value="/jobs/{jobName}", method=RequestMethod.GET)
	public String list(Model model, @PathVariable String jobName) {
		
		String job = jobName; // escape /
		
		List<JobInstanceInfo> list = new ArrayList<JobInstanceInfo>();
		
		JobInstanceFilter jobInstanceFilter = null;
		
		int count = 0;
		for (long instanceId : operator.getJobInstanceIdList(job, jobInstanceFilter)) {
			if (count++ > 100) break;
			list.add(operator.getJobInstanceInfo(job, instanceId));
		}
		
		model.addAttribute("instanceList", list);
		return "jobs/instances/list";
	}
	
//	@RequestMapping(value="/secure/events", method=RequestMethod.GET)
//	public String execute(@RequestParam String job) {
//		long instanceId = operator.execute(job, null);
//		return "redirect:/events";
//	}	
}